'use client';

import { EApi } from "@/enums";
import fetchApi from "@/utils/fetchApi";
import { useLogin } from "@/utils/hooks/useLogin";
import { useEffect, useState } from "react";
import Loading from "../../common/Loading";

export default function AuthWrapper({children} : {children: React.ReactNode}) {
    const [isLoading, setIsLoading] = useState(true);
    const { setLogined } = useLogin(); 

    useEffect(() => {
        const validateToken = async () => {
            return await fetchApi(EApi.VALIDATE, 'GET');
        }
        
        validateToken().then(() => {
            setLogined(true);
        }).catch((err) => {
            console.log("Validate token error", err);
        }).finally(() => {
            setIsLoading(false);
        });
    }, [setLogined]);

    if(isLoading) {
        return (
            <div className="flex flex-col items-center justify-center min-h-screen py-2 bg-gray-100">
                <div className="w-full max-w-md px-4 py-8 bg-white shadow-md rounded-lg">
                    <Loading/>
                </div>
            </div>
        );
    }else{
        return (
            <div className="h-full">
                {children}
            </div>
        );
    }
   
}