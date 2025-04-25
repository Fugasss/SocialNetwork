const baseUrl = 'http://localhost:8080';
const apiUrl = baseUrl + '/api';

export const EApi = {
    DEFAULT: apiUrl,

    AUTH: apiUrl + '/auth',
    LOGIN: apiUrl + '/auth/login',
    REGISTER: apiUrl + '/auth/register',
    VALIDATE: apiUrl + '/auth/validate',
    REFRESH: apiUrl + '/auth/refresh',

    USER: apiUrl + '/users',

    POST: apiUrl + '/posts',

    PUBLIC: apiUrl + '/public',
    PUBLIC_POSTS: apiUrl + '/public/posts',

} as const;

export enum ERoutes {
    HOME = '/',
    LOGIN = '/auth/login',
    REGISTRATION = '/auth/registration',
    PROFILE = '/profile',
    NEW_POST = '/forms/newpost',
};