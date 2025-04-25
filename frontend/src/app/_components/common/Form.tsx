import React from "react";

export default function Form({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex flex-col gap-4 mx-auto mt-4
                    items-center
                    w-11/12 md:w-2/3 lg:w-3/5 xl:w-1/2 2xl:w-1/3
                    ">
            {children}
        </div>);
}