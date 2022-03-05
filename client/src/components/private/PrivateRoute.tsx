import React from 'react'
import { Navigate, useNavigate } from 'react-router-dom';

interface IProps {
    children: JSX.Element;
}

const PrivateRoute = ({ children }: IProps) => {
    let token: string = localStorage.getItem("token") as string;

    if (token) 
        return children
    
    return <Navigate to="/" />
}

export default PrivateRoute
