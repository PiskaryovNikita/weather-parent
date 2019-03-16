import React from "react";
import { UserInfoComponent } from "./UserInfoComponent";
import FormComponent from "./FormComponent";

export class AddComponent extends React.Component {
    componentWillUnmount(){  
    }

    render(){
        return (
            <div>
                <UserInfoComponent/>
                <FormComponent formType="add" destination="userlist" roles={true} />
            </div>
        );
    }
}