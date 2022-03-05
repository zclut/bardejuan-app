import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <main>
            <div className="home">
                <div className="imgBx">
                    <img id="homeImg" alt="" />
                </div>


                <div className="contentBx">
                    <div className="contentName">
                        <h1>BAR DE JUAN</h1>
                        <h2>MEJOR BAR DE TENERIFE</h2>

                        <p>La mejor comida del norte de la isla</p>
                        <div className="btnYellow fw-bold fs-3 mt-3 ">
                            <Link className="text-decoration-none text-dark" to="/platos">MIRAR PLATOS</Link>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Home;