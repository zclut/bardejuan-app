import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => {


    
    return (
        <><div className="main"></div>
            <div className="footer">
                <div className="bubbles">
                    <div className="bubble" ></div>
                    <div className="bubble"></div>
                </div>
                <div className="content">
                    <div className="text-center text-white">
                        <div>Juan Daniel Padilla Obando</div>
                        <div>Bar de Juan 2022</div>
                        <div><a href="https://github.com/zclut" className="text-warning">Github - zClut</a></div>
                    </div>
                </div>
            </div>
            <svg style={{position:'fixed', top:'100vh'}}>
                <defs>
                    <filter id="blob">
                        <feGaussianBlur in="SourceGraphic" stdDeviation="10" result="blur"></feGaussianBlur>
                        <feColorMatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 19 -9" result="blob"></feColorMatrix>
                        <feComposite in="SourceGraphic" in2="blob" operator="atop"></feComposite>
                    </filter>
                </defs>
            </svg>
        </>
    );
}

export default Footer;

