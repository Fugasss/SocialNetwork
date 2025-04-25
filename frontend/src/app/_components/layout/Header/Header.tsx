'use client';

import { useLogin } from "@/utils/hooks/useLogin";
import { useEffect } from "react";
import Navigation from "./Navigation/Navigation";
import AppTitle from "./AppTitle";

export default function Header({ }) {
    const { isLogined } = useLogin();

    useEffect(() => { }, [isLogined]);
  
    return <div className="flex justify-between items-center bg-header text-white p-4">
        <AppTitle/>
        <Navigation/>
    </div>;
}