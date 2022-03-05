import React, { useState, FormEvent, useEffect } from 'react';
import plato_svg from '../../assets/img/plato.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate, useParams } from 'react-router-dom';
import { IPlato } from '../../interfaces/IPlato';
import { IDetalleFactura } from '../../interfaces/IDetalleFactura';

const NewDetalle = () => {
    const navigate = useNavigate();
    const [detalles, setDetalles] = useState<IDetalleFactura[]>([] as IDetalleFactura[]);
    const [platos, setPlatos] = useState<IPlato[]>([] as IPlato[]);
    const { idservicio } = useParams();

    useEffect(() => {
        const getPlatos = async () => {
            const response = await client.get('v2/platos?disponible=true');
            setPlatos(response.data);
        }
        getPlatos();
    }, []);

    const handleAdd = (plato: IPlato) => {
        const detalle: IDetalleFactura = {
            plato: plato,
            cantidad: 1,
            preciototal: (plato.preciounidad) ? plato.preciounidad * 1 : 0,
        }

        let detalleFinded: IDetalleFactura = detalles.filter(d => d?.plato?.nombre === plato.nombre)[0];

        if (detalleFinded) {
            let cantidad = (detalleFinded.cantidad) ? detalleFinded.cantidad + 1 : 0;
            detalleFinded = {
                ...detalleFinded,
                cantidad: cantidad,
                preciototal: (plato.preciounidad) ? plato.preciounidad * (cantidad) : 0,
            }
            setDetalles([...detalles.filter(d => d?.plato?.nombre !== plato.nombre), detalleFinded]);
        } else
            setDetalles([...detalles, detalle]);
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (detalles.length === 0)
            return Toast.fire({
                icon: 'error',
                title: 'No hay ningún plato agregado a la lista.'
            });

        await client.post(`v2/servicios/${idservicio}/platos`, detalles)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `Los detalles ha sido agregados correctamente.`
                });
                navigate(`/servicios/${idservicio}`);
            }).catch((err) => {
                Toast.fire({
                    icon: 'error',
                    title: err.response.data.message
                });
            });
    }

    const handleDelete = (nombre: any) => {
        setDetalles(detalles.filter(detalle => detalle.plato?.nombre !== nombre));
    }

    return (
        <div className="container">
            <div className="row">

                <div className="col-lg-7 col-md-12 col-12 order-last order-lg-first">
                    <div className="row">
                        {platos.map((plato: IPlato) => {

                            return <div className="col-lg-4 col-md-6 col-12 mb-4">
                                <div className="card h-100 shadow-sm" >

                                    <div className="text-center cursor-pointer">
                                        <div onClick={(e) => handleAdd(plato)} className="img-hover-zoom img-hover-zoom--colorize">
                                            <img className="shadow" src={plato_svg} alt="Imagen plato" />
                                        </div>

                                    </div>

                                    <div className="card-body">
                                        <div className="clearfix mb-3"></div>

                                        <div className="my-2 text-center">
                                            <h1>{plato.nombre}</h1>
                                        </div>

                                        <div className="mb-3">
                                            <h2 className="text-uppercase text-center role">{plato.descripcion}</h2>
                                        </div>
                                    </div>

                                    <div className="card-footer">
                                        <div className="row">
                                            <div className='col-12 d-flex'>
                                                <h3 className="my-auto mx-auto text-center">Precio: {plato.preciounidad}€</h3>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        })}
                    </div>
                </div>

                <div className="col-lg-5 col-md-12 col-12 mb-sm-5 order-first order-lg-last">
                    <div className="h-100 d-flex flex-column  bd-highlight">
                        <div className='contentName'>
                            <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Detalles</h1>

                            <form
                                onSubmit={handleSubmit}
                                className="form-group"
                            >

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
                                        {detalles?.map((d: IDetalleFactura) => {
                                            return <tr className="fs-4 text-white align-middle contentName" key={d.idfactura}>
                                                <td className='text-center'>
                                                    <div className="d-flex flex-column align-items-center justify-content-center bd-highlight">
                                                        <img className="img-table bd-highlight" src={plato_svg} alt="Imagen plato" />
                                                        <span className="bd-highlight"><b>{d.plato?.nombre}</b></span>
                                                    </div>
                                                </td>
                                                <td className='text-center' align="center">{d.cantidad}</td>
                                                <td className='text-center'>{d.plato?.preciounidad}€</td>
                                                <td className='text-center'>{d.preciototal}€</td>
                                                <td><div onClick={() => handleDelete(d.plato?.nombre)} className="fa-solid fa-x h1 editButton"></div></td>
                                            </tr>
                                        })}
                                    </tbody>
                                </table>

                                <div className="col-12 mt-5">
                                    <div className="text-end">
                                        <span className="text-white fs-1">
                                            <b>Total: </b>{detalles.reduce((total, d) => total + d.preciototal, 0)}€
                                        </span>
                                    </div>
                                </div>

                                <div className="d-grid gap-2">
                                    <button className="btnYellow mt-5 fw-bold fs-4">Agregar</button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default NewDetalle;