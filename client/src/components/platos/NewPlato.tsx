import React, { useState, FormEvent, ChangeEvent } from 'react';
import burger from '../../assets/img/burger.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate } from 'react-router-dom';
import { IPlato } from '../../interfaces/IPlato';

const NewPlato = () => {
    const navigate = useNavigate();
    const [plato, setPlato] = useState<IPlato>({} as IPlato);

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        e.preventDefault();

        setPlato({
            ...plato,
            [e.currentTarget.name]: e.currentTarget.value
        });
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const { nombre, descripcion, preciounidad, disponible } = plato;

        if (!nombre || !descripcion || !preciounidad || !disponible)
            return Toast.fire({
                icon: 'error',
                title: 'Introduce correctamente todos los datos.'
            });

        await client.post(`v2/platos`, plato)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `El plato "${res.data.nombre}" ha sido creado correctamente.`
                });
                navigate('/platos');
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
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Agregar Plato</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Nombre</span>
                                <input type="text" onChange={handleChange} name="nombre" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Descripción</span>
                                <input type="text" onChange={handleChange} name="descripcion" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Precio Unidad</span>
                                <input type="number"  onChange={handleChange} name="preciounidad" className="form-control" />
                            </div>

                            <select className="form-select form-select-lg mt-4" name="disponible" onChange={handleChange}>
                                <option disabled selected>Selecciona la disponibilidad</option>
                                <option value="true">Disponible</option>
                                <option value="false">No disponible</option>
                            </select>

                            <div className="d-grid gap-2">
                                <button className="btnYellow mt-5 fw-bold fs-4">Agregar</button>
                            </div>

                        </form>
                    </div>

                </div>

                <div className="col-md-6 col-12 imgBx">
                    <div className="h-100 d-flex flex-column align-items-center justify-content-center bd-highlight">
                        <img src={burger} alt="Imagen comensales" className="mx-auto d-block" />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default NewPlato;