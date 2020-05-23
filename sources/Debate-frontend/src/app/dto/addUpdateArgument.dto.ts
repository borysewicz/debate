import { ArgumentAttitude } from './argument.dto';

export interface AddUpdateArgument {
    title: string;
    content: string;
    attitude: ArgumentAttitude;
    debateId: string;
}
