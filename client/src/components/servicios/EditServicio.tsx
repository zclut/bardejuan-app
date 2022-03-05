import React, { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import girl from '../../assets/img/girl.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate, useParams } from 'react-router-dom';
import { IServicio } from '../../interfaces/IServicio';
import { valueInput } from '../../utils/Format';

const EditServicio = () => {
    const navigate = useNavigate();
    const [servicio, setServicio] = useState<IServicio>({} as IServicio);
    const { id } = useParams();

    useEffect(() => {
        const getServicio = async () => {
            const response = await client.get(`v2/servicios/${id}`);
            setServicio(response.data);            
        }

        getServicio();
    }, [id])


    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        e.preventDefault();

        setServicio({
            ...servicio,
            [e.currentTarget.name]: e.currentTarget.value
        });
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        await client.put(`v2/servicios/${id}`, servicio)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `El servicio ha sido modificado correctamente.`
                });
                navigate('/servicios');
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
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Modificar Servicio</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Fecha Comienzo</span>
                                <input type="datetime-local" value={valueInput((servicio.fechacomienzo)?servicio.fechacomienzo:0)}
                                    onChange={(e) => {
                                        setServicio({
                                            ...servicio,
                                            fechacomienzo: new Date(e.currentTarget.value).getTime(),
                                            fechafin: new Date(e.currentTarget.value).getTime() + 7200000
                                        })
                                    }
                                    } name="fechacomienzo" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Fecha Fin</span>
                                <input type="datetime-local" value={valueInput((servicio.fechafin)?servicio.fechafin:0)} name="fechafin" className="form-control" disabled />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Reserva</span>
                                <input type="text" value={servicio.reservada} onChange={handleChange} name="reservada" className="form-control" />
                            </div>

                            <select className="form-select form-select-lg mt-4" name="pagada" onChange={handleChange}>
                                <option value="true" selected={servicio.pagada ? true : false}>Pagada</option>
                                <option value="false" selected={!servicio.pagada ? true : false}>No Pagada</option>
                            </select>

                            <div className="d-grid gap-2">
                                <button className="btnYellow mt-5 fw-bold fs-4">Modificar</button>
                            </div>

                        </form>
                    </div>

                </div>

                <div className="col-md-6 col-12 imgBx">
                    <div className="h-100 d-flex flex-column align-items-center justify-content-center bd-highlight">
                        <img src={girl} alt="Imagen chica" className="mx-auto d-block" />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditServicio;