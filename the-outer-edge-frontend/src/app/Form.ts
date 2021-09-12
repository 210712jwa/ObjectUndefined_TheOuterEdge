import { Time } from "@angular/common";

export interface Form {
    id?: number, 
    title: string,
    description: string,
    submitted: number,
    eventTime: number,
    image: number,
    latitude: number,
    longitude: number,
    author: string,
    likes: number,
    dislikes: number,
    verifier: string, 
    formStatus: boolean,
    comments: string
}