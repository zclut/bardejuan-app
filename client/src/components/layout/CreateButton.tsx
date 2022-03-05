import React from 'react';
import { Link } from 'react-router-dom';

interface Props {
    route: string,
    text: string
}

const CreateButton = (props: Props) => {

    const { route, text } = props;

    return (
        <>

            <Link to={route} className="float">
                <i className="fas fa-plus my-float"></i>
            </Link>
            <div className="label-container">
                <div className="label-text">Crear {text}</div>
                <i className="fa fa-play label-arrow"></i>
            </div>

        </>
    );
}

export default CreateButton;