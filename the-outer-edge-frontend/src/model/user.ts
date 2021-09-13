import { UserRole } from './user-role';

export interface Users {
    id:number,
    firstName: string,
    lastName: string,
    email: string,
    username: string,
    userRole: UserRole,
}