'use client'

import { EApi } from "@/enums";
import { useAppSelector } from "@/store/store";
import fetchApi from "@/utils/fetchApi";
import { useEffect, useState } from "react";

type ProfileData = {
    username: string;
    email: string;
};

export default function Profile({}) {
    const user = useAppSelector(state => state.user);

    const [profileData, setProfileData] = useState<ProfileData>({
        username: "unknown",
        email: "unknown",
    });

    useEffect(() => {
        const fetchProfileData = async () => {
            const response = await fetchApi(EApi.USER, "GET");

            if (response?.ok) {
                const data = await response.json();
                setProfileData(data);
            } else {
                console.error("Failed to fetch profile data");
            }
        };

        fetchProfileData();
    }, [user.isLogined]);

    return <div className="flex flex-col gap-4 w-1/3 mx-auto mt-4">
        <h1 className="text-2xl">Profile</h1>
        <p className="text-gray-600">Username: {profileData.username}</p>
        <p className="text-gray-600">Email: {profileData.email}</p>
    </div>;
}