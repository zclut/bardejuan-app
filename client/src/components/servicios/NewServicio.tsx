import React, { useState, FormEvent, ChangeEvent } from 'react';
import girl from '../../assets/img/girl.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate } from 'react-router-dom';
import { IServicio } from '../../interfaces/IServicio';
import { valueInput } from '../../utils/Format';
import { IMesa } from '../../interfaces/IMesa';
import { useEffect } from 'react';

const NewServicio = () => {
    const navigate = useNavigate();
    const [servicio, setServicio] = useState<IServicio>({} as IServicio);
    const [mesas, setMesas] = useState<IMesa[]>([] as IMesa[]);

    useEffect(() => {
        const { fechacomienzo, ocupantes } = servicio;
        if (fechacomienzo && ocupantes) {

            const checkReserva = async () => {
                await client.get(`v2/mesas?fecha=${fechacomienzo}&ocupantes=${ocupantes}`)
                    .then((res) => {
                        setMesas(res.data);
                    }).catch((err) => {
                        setMesas([]);
                        Toast.fire({
                            icon: 'error',
                            title: err.response.data.message
                        });
                    });
            }
            checkReserva();
        }
    }, [servicio.fechacomienzo, servicio.ocupantes])


    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        e.preventDefault();

        setServicio({
            ...servicio,
            [e.currentTarget.name]: e.currentTarget.value
        });
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        await client.post(`v2/servicios`, servicio)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `El servicio ha sido creado correctamente.`
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
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Crear Servicio</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Fecha Comienzo</span>
                                <input type="datetime-local"
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
                                <span className="input-group-text" id="inputGroup-sizing-lg">Reserva</span>
                                <input type="text" onChange={handleChange} name="reservada" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Ocupantes</span>
                                <input type="number" onChange={handleChange} name="ocupantes" className="form-control" min={1} />
                            </div>

                            <select className="form-select form-select-lg mt-4" name="pagada" onChange={handleChange}>
                                <option disabled selected>Selecciona el pago</option>
                                <option value="true">Pagada</option>
                                <option value="false">No Pagada</option>
                            </select>

                            <select className="form-select form-select-lg mt-4" name="nummesa"
                                onChange={(e) => setServicio({ ...servicio, mesa: { [e.currentTarget.name]: e.currentTarget.value } })}>

                                <option disabled selected>Selecciona la mesa</option>
                                {mesas.map(mesa => {
                                    const { nummesa, ocupantesmax } = mesa;
                                    return <option key={`mesa${nummesa}`} value={nummesa}>Mesa Nº{nummesa} - {ocupantesmax} Ocupantes</option>
                                })}

                            </select>



                            <div className="d-grid gap-2">
                                <button className="btnYellow mt-5 fw-bold fs-4">Agregar</button>
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

export default NewServicio;