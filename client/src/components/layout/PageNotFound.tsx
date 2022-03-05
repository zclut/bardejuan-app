import React from 'react'
import { Link } from 'react-router-dom';
import notfound from '../../assets/img/404.svg';

const PageNotFound = () => {
    return (
        <div className="container">
            <div className="row">
                <div className="col-12">
                    <div className="d-flex d-flex flex-column align-items-center justify-content-center bd-highlight">
                        <img src={notfound} alt="Image not found" className='img-fluid mx-auto d-block' />

                        <Link to="/">
                            <div className="btnYellow mt-5 fs-3">
                                Volver al inicio
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PageNotFound;