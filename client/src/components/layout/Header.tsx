import React, { MouseEvent, useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import client from '../../config/axios';
import tokenAuth from '../../config/token';
import { IOperario } from '../../interfaces/IOperario';
import { AlertModal } from './Alerts';

const Header = () => {

    const navigate = useNavigate();
    const [isAuth, setAuth] = useState<Boolean>(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        setAuth((token) ? true : false)
    }, [])


    const handleMenuClick = (e: MouseEvent<HTMLDivElement>) => {
        e.preventDefault();

        const navBar = document.getElementById('navBar') as HTMLElement;
        navBar.classList.toggle('active');
    }

    const handleLogout = (e: MouseEvent<HTMLDivElement>) => {
        e.preventDefault();

        const token = localStorage.getItem('token');

        if (token) {
            localStorage.clear();
            setAuth(false);
            tokenAuth('');
            AlertModal('success', 'Exito', 'Has cerrado sesión correctamente.');
            navigate('/');
        } 
    }

    const handleLogin = (e: MouseEvent<HTMLDivElement>) => {
        e.preventDefault();

        Swal.fire({
            title: 'Iniciar Sesión',
            html: `
            <input type="text" id="nombre" class="swal2-input" placeholder="Usuario">
            <input type="password" id="password" class="swal2-input" placeholder="Contraseña">`,
            confirmButtonText: 'Login',
            focusConfirm: false,
            background: '#3b3228',
            color: '#fff',
            confirmButtonColor: '#4c4034',
            preConfirm: async () => {
                const nombre = (Swal.getPopup()?.querySelector('#nombre') as HTMLInputElement).value;
                const password = (Swal.getPopup()?.querySelector('#password') as HTMLInputElement).value;
                const data: IOperario = {
                    nombre: nombre,
                    password: password
                }

                await client.post('login', data)
                    .then(res => {
                        localStorage.setItem('token', res.data);
                        tokenAuth(res.data);
                        setAuth(true);
                        navigate('/');
                        AlertModal('success', 'Exito', 'Has iniciado sesión correctamente.')
                    }).catch(error =>
                        Swal.showValidationMessage(error.response.data.message)
                    );
            }
        });
    }

    return (
        <header className="header">

            <Link to="/" className="logo">
                <i className="fa-solid fa-burger"></i>
                Bar de Juan
                <i className="fa-solid fa-burger"></i>
            </Link>

            <nav className="navbar" id="navBar">
                <Link to="/">Inicio</Link>
                <Link to="/platos">Platos</Link>
                {isAuth
                    ? (
                        <>
                            <Link to="/mesas">Mesas</Link>
                            <Link to="/servicios">Servicios</Link>
                        </>
                    )
                    : null
                }

            </nav>

            <div className="options">
                {
                    (isAuth)
                    ? <div title="Cerrar sesión" onClick={handleLogout} className="fas fa-sign-in-alt" id="logout-btn"></div>
                    : <div title="Iniciar sesión" onClick={handleLogin} className="fa-solid fa-user" id="login-btn"></div>
                }
                <div onClick={handleMenuClick} className="fas fa-bars" id="menu-btn"></div>
            </div>
        </header>
    );
}

export default Header;
