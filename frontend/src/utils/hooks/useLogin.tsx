import { setIsLogined, setUserId } from "@/store/slices/userSlice";
import { useAppDispatch, useAppSelector } from "@/store/store";
import { useCallback } from "react";
import { getCookie } from "../cookie";
import { parseJwt } from "../jwt";

export const useLogin = () => {
    const isLogined = useAppSelector((state) => state.user.isLogined);
    const dispatch = useAppDispatch();

    const setLogined = useCallback((status: boolean) => {
        const token = getCookie("token");

        if (!token) {
            console.log("Token not found, setting isLogined to false");
            dispatch(setIsLogined(false));
            dispatch(setUserId(null));
            return;
        }

        const userId = parseJwt(token).sub;

        dispatch(setIsLogined(status));
        dispatch(setUserId(userId));
    }, [dispatch]);

    return { isLogined, setLogined };
};