import { UserRole } from './user-role';

export interface Users {
    firstName: string,
    lastName: string,
    email: string,
    username: string,
    userRole: UserRole,
}