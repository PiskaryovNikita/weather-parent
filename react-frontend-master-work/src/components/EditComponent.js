import React from "react";
import { UserInfoComponent } from "./UserInfoComponent";
import FormComponent from "./FormComponent";

export class EditComponent extends React.Component {
    componentWillUnmount(){
    }

    render(){
        return (
            <div>
                <UserInfoComponent/>
                <FormComponent formType="edit" destination="userlist" roles={true} />
            </div>
        );
    }
}