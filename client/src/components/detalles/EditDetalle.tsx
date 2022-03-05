import React, { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import persons from '../../assets/img/persons.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate, useParams } from 'react-router-dom';
import { IDetalleFactura } from '../../interfaces/IDetalleFactura';

const EditDetalle = () => {
    const navigate = useNavigate();
    const [detalle, setDetalle] = useState<IDetalleFactura>({} as IDetalleFactura);
    const { idservicio, idfactura } = useParams();

    useEffect(() => {
        const getDetalle = async () => {
            const response = await client.get(`v2/servicios/${idservicio}/platos/${idfactura}`);            
            setDetalle(response.data);
        }

        getDetalle();
    }, [idservicio, idfactura]);


    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();

        setDetalle({
            ...detalle,
            [e.currentTarget.name]: e.currentTarget.value
        });
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        await client.put(`v2/servicios/${idservicio}/platos/${idfactura}`, detalle)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `El detalle ha modificado correctamente.`
                });
                navigate(`/servicios/${idservicio}`);
            }).catch((err) => {
                Toast.fire({
                    icon: 'error',
                    title: err.response.data.message
                });
            });
    }

    return (
        <div className="container">
            <div className="row">

                <div className="col-md-6 col-12 mb-sm-5 ">
                    <div className="h-100 contentName d-flex flex-column align-items-center justify-content-center bd-highlight">
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Modificar Detalle</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Plato</span>
                                <input type="text" value={detalle.plato?.nombre} name="plato" className="form-control"/>
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Cantidad</span>
                                <input type="number" value={detalle.cantidad} onChange={handleChange} name="cantidad" className="form-control" min={1} />
                            </div>

                            <div className="d-grid gap-2">
                                <button className="btnYellow mt-5 fw-bold fs-4">Modificar</button>
                            </div>

                        </form>
                    </div>
                </div>

                <div className="col-md-6 col-12 imgBx">
                    <div className="h-100 d-flex flex-column align-items-center justify-content-center bd-highlight">
                        <img src={persons} alt="Imagen comensales" className="mx-auto d-block" />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditDetalle;