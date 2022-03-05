import React, { useState, useEffect } from 'react';
import client from '../../config/axios';
import servicio_svg from '../../assets/img/service.svg';
import { IServicio } from '../../interfaces/IServicio';
import { AlertModal, ConfirmModal, Toast } from '../layout/Alerts';
import { Link } from 'react-router-dom';
import { formatDate } from '../../utils/Format';
import CreateButton from '../layout/CreateButton';
import FilterButton from '../layout/FilterButton';

const ListServicios = () => {

    const [servicios, setServicios] = useState<IServicio[]>([] as IServicio[]);

    const getServicios = async (pagado?: string) => {
        const response = await client.get((pagado)? `v2/servicios?pagado=${pagado}`: 'v2/servicios');
        setServicios(response.data);
    }

    useEffect(() => {
        getServicios();
    }, [])

    const changeStatus = async (id: any, status: any) => {
        const data: IServicio = {
            pagada: (status) ? false : true
        }
        await client.put(`v2/servicios/${id}`, data)
            .then((res) => {
                setServicios(servicios.map(servicio => servicio.idservicio === id ? res.data : servicio));
                Toast.fire({
                    icon: 'success',
                    title: 'Actualizado correctamente'
                });
            })
            .catch((err) => {
                Toast.fire({
                    icon: 'error',
                    title: err.response.data.message
                });
            });
    }

    const handleDelete = async (id: any) => {
        ConfirmModal('Advertencia', 'Estas seguro de eliminar este servicio?')
            .then(async (result) => {
                if (result.isConfirmed) {
                    await client.delete(`v2/servicios/${id}`);
                    setServicios(servicios.filter(servicio => servicio.idservicio !== id));
                    AlertModal('success', 'Exito', 'Eliminado correctamente');
                }
            })
            .catch((err) => {
                Toast.fire({
                    icon: 'error',
                    title: err.response.data.message
                });
            });
    }


    return (
        <>
            <div className='container'>

                <h1 className='display-3 text-white text-center'>Lista de Servicios</h1>

                <div className="row">


                    <FilterButton handleOnClick={() => getServicios('false')} text="No Pagados" position="text-md-end text-center mb-2" />
                    <FilterButton handleOnClick={() => getServicios()} text="Todos" position="text-center mb-2"/>
                    <FilterButton handleOnClick={() => getServicios('true')} text="Pagados" position="text-md-start text-center" />

                    {servicios.map((servicio: IServicio) => {

                        const { idservicio, fechacomienzo, reservada, pagada } = servicio;

                        return (
                            <div className="col-md-3 col-sm-6 col-12 mt-4" key={idservicio}>
                                <div className="card h-100 shadow-sm" >

                                    <div className="position-absolute top-0 start-0 ms-2 mt-2">
                                        <Link to={`/servicios/${idservicio}/edit`}>
                                            <div className="fa-solid fa-pencil h1 editButton"></div>
                                        </Link>
                                    </div>

                                    <div onClick={() => handleDelete(idservicio)} className="position-absolute top-0 end-0 me-2 mt-2">
                                        <div className="fa-solid fa-x h1 editButton"></div>
                                    </div>

                                    <Link to={`/servicios/${idservicio}`}>
                                        <div className="text-center">

                                            <div className="img-hover-zoom img-hover-zoom--colorize">
                                                <img className="shadow" src={servicio_svg} alt="Imagen plato" />
                                            </div>

                                        </div>
                                    </Link>


                                    <div className="card-body">
                                        <div className="clearfix mb-3"></div>

                                        <div className="my-2 text-center">
                                            <h1>Servicio</h1>
                                        </div>

                                        <div className="mb-3">
                                            <h2 className="text-uppercase text-center role">{reservada}</h2>
                                        </div>
                                    </div>

                                    <div className="card-footer">
                                        <div className="row">
                                            <div className='col-6'>
                                                <h3 className="my-auto mx-auto text-center">{formatDate(fechacomienzo)}</h3>
                                            </div>
                                            <div className="col-6 d-flex estado">
                                                {pagada
                                                    ? <button
                                                        type="button"
                                                        onClick={() => changeStatus(idservicio, pagada)}
                                                        className="my-auto mx-auto completo">Pagado</button>
                                                    : <button
                                                        type="button"
                                                        onClick={() => changeStatus(idservicio, pagada)}
                                                        className="my-auto mx-auto incompleto">No Pagado</button>
                                                }

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>

            <CreateButton route="/servicios/create" text="servicio" />
        </>
    );
}

export default ListServicios;