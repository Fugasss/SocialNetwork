export interface PageableResponse<T> {
    content: T[];
    number: number;
    totalPages: number;
    first: boolean;
    last: boolean;
}
