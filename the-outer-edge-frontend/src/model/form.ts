import { Users } from './user';
import { FormStatus } from './form-status';
import{ Comment } from './comment'


export interface Form {
    id: number,
    title: string,
    description: string,
    submitted: string,
    eventTime: string,
    latitude: number,
    longitude: number,
    image: string,
    author:Users,
    like:number,
    dislike:number,
    verifier: Users,
    comments: Comment[],
    formStatus: FormStatus
}