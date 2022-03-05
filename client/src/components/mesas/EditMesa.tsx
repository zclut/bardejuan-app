import React, { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import { IMesa } from '../../interfaces/IMesa';
import persons from '../../assets/img/persons.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate, useParams } from 'react-router-dom';

const EditMesa = () => {
    const navigate = useNavigate();
    const [mesa, setMesa] = useState<IMesa>({} as IMesa);
    const { id } = useParams();

    useEffect(() => {
        const getMesa = async () => {
            console.log(id);

            const response = await client.get(`v2/mesas/${id}`);
            console.log(response.data);

            setMesa(response.data);
        }

        getMesa();
    }, [id])


    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();

        setMesa({
            ...mesa,
            [e.currentTarget.name]: e.currentTarget.value
        });
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const { nummesa, ocupantesmax } = mesa;

        if (!nummesa || !ocupantesmax)
            return Toast.fire({
                icon: 'error',
                title: 'Introduce correctamente todos los datos'
            });

        await client.put(`v2/mesas/${id}`, mesa)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `Mesa Nº ${res.data.nummesa} modificada correctamente`
                });
                navigate('/mesas');
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
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Modificar Mesa</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Nº Mesa</span>
                                <input type="number" value={mesa.nummesa} onChange={handleChange} name="nummesa" className="form-control" min={1} />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Ocupantes Maximos</span>
                                <input type="number" value={mesa.ocupantesmax} onChange={handleChange} name="ocupantesmax" className="form-control" min={1} />
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

export default EditMesa;