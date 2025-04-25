import { createSlice } from "@reduxjs/toolkit";

type User = {
    id: string;
    isLogined: boolean;
}

const initialState: User = {
    id: "",
    isLogined: false,
};

const userSlice = createSlice({
    name: "user",
    initialState: initialState,
    reducers: {
        setIsLogined: (state, action) => {
            state.isLogined = action.payload;
        },
        setUserId: (state, action) => {
            state.id = action.payload;
        },
    },
});

export const { setIsLogined, setUserId } = userSlice.actions;
export default userSlice.reducer;