import React, { useState, useEffect } from 'react';
import client from '../../config/axios';
import { IPlato } from '../../interfaces/IPlato';
import plato_svg from '../../assets/img/plato.svg';
import { Toast } from '../layout/Alerts';
import { Link } from 'react-router-dom';
import CreateButton from '../layout/CreateButton';
import FilterButton from '../layout/FilterButton';

const ListPlatos = () => {

    const [platos, setPlatos] = useState<IPlato[]>([] as IPlato[]);
    const [isAuth, setAuth] = useState<Boolean>(false);
    const getPlatos = async (disponible?: string) => {
        const token: string = localStorage.getItem('token') as string;
        const response = await client.get(
            (token) ? (disponible) ? `v2/platos?disponible=${disponible}` : 'v2/platos' : 'v1/platos');
        setPlatos(response.data);
    }

    useEffect(() => {
        const getAuth = () => {
            const token = localStorage.getItem('token');
            setAuth((token) ? true : false)
        }

        getAuth();
        getPlatos();
    }, [])

    const changeStatus = async (id: any, status: any) => {
        const data: IPlato = {
            disponible: (status) ? false : true
        }
        await client.put(`v2/platos/${id}`, data)
            .then((res) => {
                setPlatos(platos.map(plato => plato.idplato === id ? res.data : plato));
                Toast.fire({
                    icon: 'success',
                    title: 'Actualizado correctamente'
                });
            }).catch((err) => {
                Toast.fire({
                    icon: 'error',
                    title: err.response.data.message
                });
            });
    }


    return (
        <>
            <div className='container'>

                <h1 className='display-3 text-white text-center'>Lista de Platos</h1>

                <div className="row">

                    {isAuth ? (
                        <>
                            <FilterButton handleOnClick={() => getPlatos('false')} text="No Disponibles" position="text-md-end text-center mb-2" />
                            <FilterButton handleOnClick={() => getPlatos()} text="Todos" position="text-center mb-2" />
                            <FilterButton handleOnClick={() => getPlatos('true')} text="Disponibles" position="text-md-start text-center" />
                        </>
                    ) : null}


                    {platos.map((plato: IPlato) => {

                        const { idplato, nombre, descripcion, disponible, preciounidad } = plato;

                        return (
                            <div className="col-md-3 col-sm-6 col-12 mt-4" key={idplato}>

                                <div className="card h-100 shadow-sm" >

                                    {isAuth
                                        ?
                                        <div className="position-absolute top-0 start-0 ms-2 mt-2">
                                            <Link to={`/platos/${plato.idplato}/edit`}>
                                                <div className="fa-solid fa-pencil h1 editButton"></div>
                                            </Link>
                                        </div>
                                        : null
                                    }

                                    <div className="text-center">
                                        <div className="img-hover-zoom img-hover-zoom--colorize">
                                            <img className="shadow" src={plato_svg} alt="Imagen plato" />
                                        </div>

                                    </div>

                                    <div className="card-body">
                                        <div className="clearfix mb-3"></div>

                                        <div className="my-2 text-center">
                                            <h1>{nombre}</h1>
                                        </div>

                                        <div className="mb-3">
                                            <h2 className="text-uppercase text-center role">{descripcion}</h2>
                                        </div>
                                    </div>

                                    <div className="card-footer">
                                        <div className="row">
                                            <div className={`${isAuth ? 'col-6' : 'col-12'} d-flex`} >
                                                <h3 className="my-auto mx-auto text-center">Precio: {preciounidad}€</h3>
                                            </div>
                                            {
                                                isAuth
                                                    ?
                                                    <div className="col-6 d-flex estado">
                                                        {disponible
                                                            ? <button
                                                                type="button"
                                                                onClick={() => changeStatus(idplato, disponible)}
                                                                className="my-auto mx-auto completo">Disponible</button>
                                                            : <button
                                                                type="button"
                                                                onClick={() => changeStatus(idplato, disponible)}
                                                                className="my-auto mx-auto incompleto">No Disponible</button>
                                                        }

                                                    </div>
                                                    : null
                                            }

                                        </div>
                                    </div>

                                </div>

                            </div>
                        )
                    })}
                </div>
            </div>

            {isAuth ? <CreateButton route="/platos/create" text="plato" /> : null}

        </>
    );
}

export default ListPlatos;