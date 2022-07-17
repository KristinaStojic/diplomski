import { Role } from './role';
export class Auth {
    token: string;
    role: Role;
    
    constructor(token : string,role : Role){
        this.token = token;
        this.role = role;
    }

}
