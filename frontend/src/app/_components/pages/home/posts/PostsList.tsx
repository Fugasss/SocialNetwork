import { EApi } from "@/enums";
import { setCurrentPage, setPagesCount, setPostListIsActual, setPosts } from "@/store/slices/postListSlice";
import { useAppDispatch, useAppSelector } from "@/store/store";
import fetchApi from "@/utils/fetchApi";
import { useEffect, useState } from "react";
import Post from "./Post";
import Button from "@/app/_components/common/buttons/Button";
import { PageableResponse } from "../../../common/PageableResponse";
import Loading from "@/app/_components/common/Loading";

interface PostDto {
    id: string;
    authorId: string;
    title: string;
    content: string;
    authorName: string;
    createdAt: Date;
}

export default function PostsList({ }) {
    const postsData = useAppSelector(state => state.postList);
    const userId = useAppSelector(state => state.user.id);
    const dispatch = useAppDispatch();
    const [isFirstPage, setIsFirstPage] = useState(true);
    const [isLastPage, setIsLastPage] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        if (postsData.isActual) {
            return;
        }

        console.log("PostsList: ", postsData);

        const getPosts = async () => {
            setIsLoading(true);

            const response = await fetchApi(`${EApi.PUBLIC_POSTS}?page=${postsData.currentPage}`, "GET");

            if (response?.ok) {
                const pageable = await response.json();

                console.log("Pageable: ", pageable);

                dispatch(setPosts(pageable.content));
                dispatch(setPostListIsActual(true));
                dispatch(setPagesCount(pageable.totalPages));
                dispatch(setCurrentPage(pageable.number));
                setIsLoading(false);
            }
        };

        getPosts();

    }, [postsData.isActual, postsData.currentPage, dispatch]);

    const updatePageState = (response: PageableResponse<PostDto>) => {
        dispatch(setPosts(response.content));
        dispatch(setCurrentPage(response.number));
        setIsFirstPage(response.first);
        setIsLastPage(response.last);
    };

    const handleNextPage = async () => {
        if (postsData.currentPage + 1 >= postsData.pagesCount) {
            return;
        }
        const response = await fetchApi(`${EApi.PUBLIC_POSTS}?page=${postsData.currentPage + 1}`, "GET");
        if (response?.ok) {
            updatePageState(await response.json());
        }
    };

    const handlePrevPage = async () => {
        if (postsData.currentPage - 1 < 0) {
            return;
        }
        const response = await fetchApi(`${EApi.PUBLIC_POSTS}?page=${postsData.currentPage - 1}`, "GET");
        if (response?.ok) {
            updatePageState(await response.json());
        }
    };

    return <>
        <div>
            <div className="flex flex-row gap-4 justify-between items-center">
                <Button text="Prev" handleClick={handlePrevPage} disabled={isFirstPage} />
                <Button text="Next" handleClick={handleNextPage} disabled={isLastPage || postsData.pagesCount <= 1} />
            </div>
            <p>{postsData.currentPage + 1}/{postsData.pagesCount}</p>
        </div>
        <div className="flex flex-col gap-4">
            <div className="flex flex-col gap-2">
                <h1 className="text-2xl font-bold">Posts</h1>
                <div className="w-full h-1 bg-black p-0 m-0" />
            </div>
            <div className="flex flex-col gap-4">
                {isLoading && <Loading />}
                {postsData.posts.map((post) => <Post key={post.id} {...post} canBeDeleted={post.authorId === userId} />)}
            </div>
        </div>
    </>;
}