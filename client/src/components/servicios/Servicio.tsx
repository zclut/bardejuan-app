import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import client from '../../config/axios';
import { IServicio } from '../../interfaces/IServicio';
import { formatDate } from '../../utils/Format';
import plato from '../../assets/img/plato.svg';
import { IDetalleFactura } from '../../interfaces/IDetalleFactura';
import { AlertModal, ConfirmModal, Toast } from '../layout/Alerts';
import CreateButton from '../layout/CreateButton';

const Servicio = () => {

    const [servicio, setServicio] = useState<IServicio>({} as IServicio);
    const { id } = useParams();

    useEffect(() => {
        const getServicio = async () => {
            const response = await client.get(`v2/servicios/${id}`);
            setServicio(response.data);
        }
        getServicio();
    }, [id])

    const handleDelete = async (idfactura: any) => {
        ConfirmModal('Advertencia', 'Estas seguro de eliminar este detalle?')
            .then(async (result) => {
                if (result.isConfirmed) {
                    await client.delete(`v2/servicios/${id}/platos/${idfactura}`);
                    setServicio({
                        ...servicio,
                        detalleFactura: servicio?.detalleFactura?.filter(detalle => detalle.idfactura !== idfactura)
                    });
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
            <div className="container contentName">
                <div className="row">
                    <div className="col-md-6 col-12">
                        <div className="logo text-center text-md-start">
                            <i className="fa-solid fa-burger"></i>
                            <span className="text-white display-5">Bar de Juan</span>
                            <i className="fa-solid fa-burger"></i>
                        </div>
                    </div>

                    <div className="col-md-6 col-12 text-md-end mt-sm-2">
                        <div className="row">
                            <div className="col-12 text-white">
                                <span className="fs-4"><b>Fecha comienzo: </b>{formatDate(servicio.fechacomienzo)}</span>
                            </div>
                            <div className="col-12 text-white">
                                <span className="fs-4"><b>Fecha fin: </b>{formatDate(servicio.fechafin)}</span>
                            </div>
                        </div>
                    </div>

                    <div className="col-md-4 col-12 text-start">
                        <span className="text-white fs-4"><b>Concepto: </b>{servicio.reservada}</span>
                    </div>

                    <div className="col-md-4 col-12 text-md-center">
                        <span className="text-white fs-4"><b>Mesa Nº{servicio.mesa?.nummesa}</b></span>
                    </div>

                    <div className="col-md-4 col-12 text-md-end">
                        <span className='text-white fs-4'><b>Estado: </b>{servicio.pagada ? "Pagado" : "No pagado"}</span>
                    </div>

                    <div className="col-12 mt-5">
                        <div className="text-center">
                            <span className='text-white fs-1 text-decoration-underline'>DETALLES</span>

                        </div>
                    </div>

                    <div className="col-12 mt-5">
                        <table className="table table-borderless table-responsive">
                            <thead>
                                <tr className='text-white fs-4'>
                                    <th className='text-center' scope="col"></th>
                                    <th className='text-center' scope="col">Cantidad</th>
                                    <th className='text-center' scope="col">Precio</th>
                                    <th className='text-center' scope="col">Total</th>
                                    <th className='text-center' scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                                {servicio.detalleFactura?.map((d: IDetalleFactura) => {
                                    return <tr className="fs-4 text-white align-middle contentName" key={d.idfactura}>
                                        <td className='text-center'>
                                            <Link className="text-white text-decoration-none" to={`/servicios/${id}/platos/${d.idfactura}/edit`}>
                                                <div className="d-flex flex-column align-items-center justify-content-center bd-highlight">
                                                    <img className="img-table bd-highlight" src={plato} alt="Imagen plato" />
                                                    <span className="bd-highlight"><b>{d.plato?.nombre}</b></span>
                                                </div>
                                            </Link>
                                        </td>
                                        <td className='text-center' align="center">{d.cantidad}</td>
                                        <td className='text-center'>{d.plato?.preciounidad}€</td>
                                        <td className='text-center'>{d.preciototal}€</td>
                                        <td><div onClick={() => handleDelete(d.idfactura)} className="fa-solid fa-x h1 editButton"></div></td>
                                    </tr>
                                })}
                            </tbody>
                        </table>
                    </div>

                    <div className="col-12 mt-5">
                        <div className="text-end">
                            <span className="text-white fs-1">
                                <b>Total: </b>{servicio?.detalleFactura?.reduce((total, d) => total + d.preciototal, 0)}€
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <CreateButton route={`platos/create`} text="detalle" />
        </>
    );
}

export default Servicio;