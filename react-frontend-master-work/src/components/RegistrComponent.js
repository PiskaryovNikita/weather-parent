import React from "react";
import FormComponent from "./FormComponent";

export class RegistrComponent extends React.Component {
    componentWillUnmount(){
    }

    render(){
        return (
                <FormComponent formType="registr" destination="login" roles={false} />
        );
    }
}