import { Users } from './user';
import { FormStatus } from './form-status';
import{ Comment } from './comment'


export interface Form {
    id: number,
    title: string,
    description: string,
    submitted: string,
    eventTime: string,
    image: any,
    latitude: number,
    longitude: number,
    author:Users,
    likes:number,
    dislikes:number,
    verifier: Users,
    comments: Comment[],
    formStatus: FormStatus
}