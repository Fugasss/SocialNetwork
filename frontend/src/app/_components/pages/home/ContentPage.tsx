import { useLogin } from "@/utils/hooks/useLogin";
import PostsList from "./posts/PostsList";
import Button from "../../common/buttons/Button";
import { useRouter } from "next/navigation";
import { ERoutes } from "@/enums";

export default function ContentPage({ }) {

    const { isLogined } = useLogin();
    const router = useRouter();

    return <div className="flex flex-col gap-4 mx-auto mt-4
                            w-11/12 md:w-2/3 lg:w-3/5 xl:w-1/2 2xl:w-1/3">
        {isLogined && <Button text="New Post" handleClick={()=>{router.push(ERoutes.NEW_POST)}}/>}
        <PostsList />
    </div>;
}