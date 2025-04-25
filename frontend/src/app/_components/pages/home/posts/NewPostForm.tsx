'use client';

import Button from "@/app/_components/common/buttons/Button";
import Form from "@/app/_components/common/Form";
import TextArea from "@/app/_components/common/inputs/TextArea";
import TextInput from "@/app/_components/common/inputs/TextInput";
import Loading from "@/app/_components/common/Loading";
import { EApi, ERoutes } from "@/enums";
import { setPostListIsActual } from "@/store/slices/postListSlice";
import { useAppDispatch } from "@/store/store";
import fetchApi from "@/utils/fetchApi";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function NewPostForm({ }) {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const [isSavingPost, setIsSavingPost] = useState(false);
    const [error, setError] = useState("");

    const dispatcher = useAppDispatch();
    const router = useRouter();

    const handleClick = async () => {
        setIsSavingPost(true);

        if (!title || !content) {
            setError("Title and content are required");
            return;
        }

        const response = await fetchApi(EApi.POST, "POST", { title, content });
        console.log(response);

        if (!response?.ok) {
            setError(response?.statusText || "Error while creating post");
        } else {
            setError("");
            setTitle("");
            setContent("");
            dispatcher(setPostListIsActual(false));
            router.push(ERoutes.HOME);
        }

        setIsSavingPost(false);
    }

    return (<Form>
        <Button text="Return" handleClick={() => { router.push(ERoutes.HOME) }} />
        
        <p className="text-gray-500">Do you want to share something today?</p>

        <TextInput placeholder="Title" value={title} handleChange={setTitle} />
        <TextArea placeholder="Content" value={content} handleChange={setContent} rows={4} />

        <Button text="Create Post" handleClick={handleClick} disabled={isSavingPost} />
        {isSavingPost && <Loading />}
        {error && <p className="text-red-500">{error}</p>}
    </Form>);
}