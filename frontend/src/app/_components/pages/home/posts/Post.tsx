import DeleteButton from "@/app/_components/common/buttons/DeleteButton";
import { EApi } from "@/enums";
import { setPostListIsActual } from "@/store/slices/postListSlice";
import { useAppDispatch } from "@/store/store";
import fetchApi from "@/utils/fetchApi";

type PostProps = {
    id: string;
    title: string;
    content: string;
    authorName: string;
    createdAt: Date;
    canBeDeleted?: boolean;
};

export default function Post({
    id,
    title,
    content,
    authorName,
    createdAt,
    canBeDeleted
}: PostProps) {
    canBeDeleted = canBeDeleted ?? false;
    
    const dispatcher = useAppDispatch();

    const dateFormatted = (date: Date) => {
        return date.toLocaleDateString("ru-RU") + " " + date.toLocaleTimeString("ru-RU");
    };

    const handleDelete = async () => {
        await fetchApi(`${EApi.POST}/${id}`, "DELETE");
        dispatcher(setPostListIsActual(false));
    };

    return (
        <div className="flex flex-col gap-2 border-b-2 pb-4">
            <div className="flex flex-row">
                {canBeDeleted && <DeleteButton icon="cross.svg" handleClick={handleDelete} width={16} height={16}/>}
            </div>
            <h1 className="text-xl font-bold text-wrap overflow-x-hidden wrap-break-word">{title}</h1>
            <p className="text-gray-500 text-wrap wrap-break-word">{content}</p>
            <p className="text-gray-400 text-sm overflow-x-hidden">{ `by ${authorName} at ${dateFormatted(new Date(createdAt))}`}</p>
        </div>
    );
}