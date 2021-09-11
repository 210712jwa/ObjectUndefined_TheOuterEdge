import { Users } from './user';

export interface Comment {
    id: number,
    content: String,
    author: Users,
    like: number,
    dislike: number
}