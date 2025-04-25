import { createSlice } from "@reduxjs/toolkit";

type Post = {
    id: string;
    title: string;
    content: string;
    authorName: string;
    authorId: string;
    createdAt: Date;
}

type PostList = {
    posts: Post[];
    pagesCount: number;
    currentPage: number;
    isActual: boolean;
}

const initialState: PostList = {
    posts: [],
    isActual: false,
    pagesCount: 0,
    currentPage: 0,
};

const postListSlice = createSlice({
    name: "postList",
    initialState: initialState,
    reducers: {
        setPostListIsActual: (state, action) => {
            state.isActual = action.payload;
        },
        setPosts: (state, action) => {
            state.posts = action.payload;
        },
        setPagesCount: (state, action) => {
            state.pagesCount = action.payload;
        },
        setCurrentPage: (state, action) => {
            state.currentPage = action.payload;
        }
    },
});

export const { setPostListIsActual, setPosts, setCurrentPage, setPagesCount } = postListSlice.actions;
export default postListSlice.reducer;

