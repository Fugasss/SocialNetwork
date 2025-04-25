'use client';

import Button from "@/app/_components/common/buttons/Button";
import { ERoutes } from "@/enums";
import { setCookie } from "@/utils/cookie";
import { useLogin } from "@/utils/hooks/useLogin";
import { useRouter } from "next/navigation";

export default function Navigation({}) {
    const router = useRouter();
    const {isLogined, setLogined} = useLogin();

    const handleLogout = () => {
        setCookie('token', '');
        setCookie('refreshToken', '');
        setLogined(false);

        console.log("User logged out");
    };

    return (
        <nav className="flex space-x-4 p-0 m-0">
            <Button text="Home" handleClick={()=>{router.push(ERoutes.HOME)}}/>
            {isLogined && <Button text="Profile" handleClick={()=>{router.push(ERoutes.PROFILE)}}/>}
            {isLogined && <Button text="Logout" handleClick={handleLogout}/>}
            {!isLogined && <Button text="Login" handleClick={()=>{router.push(ERoutes.LOGIN)}}/>}
            {!isLogined && <Button text="Register" handleClick={()=>{router.push(ERoutes.REGISTRATION)}}/>}
        </nav>
    );
}