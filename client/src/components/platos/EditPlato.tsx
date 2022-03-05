import React, { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import burger from '../../assets/img/burger.svg';
import { Toast } from '../layout/Alerts';
import client from '../../config/axios';
import { useNavigate, useParams } from 'react-router-dom';
import { IPlato } from '../../interfaces/IPlato';

const EditPlato = () => {
    const navigate = useNavigate();
    const [plato, setPlato] = useState<IPlato>({} as IPlato);
    const { id } = useParams();

    useEffect(() => {
        const getMesa = async () => {
            console.log(id);

            const response = await client.get(`v2/platos/${id}`);
            console.log(response.data);

            setPlato(response.data);
        }

        getMesa();
    }, [id])


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

        await client.put(`v2/platos/${id}`, plato)
            .then((res) => {
                Toast.fire({
                    icon: 'success',
                    title: `El plato "${res.data.nombre}" ha sido modificado correctamente.`
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
                        <h1 className='display-3 text-white text-center mb-5 bd-highlight'>Modificar Plato</h1>

                        <form
                            onSubmit={handleSubmit}
                            className="form-group"
                        >

                            <div className="input-group input-group-lg">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Nombre</span>
                                <input type="text" value={plato.nombre} onChange={handleChange} name="nombre" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Descripción</span>
                                <input type="text" value={plato.descripcion} onChange={handleChange} name="descripcion" className="form-control" />
                            </div>

                            <div className="input-group input-group-lg mt-4">
                                <span className="input-group-text" id="inputGroup-sizing-lg">Precio Unidad</span>
                                <input type="number" value={plato.preciounidad} onChange={handleChange} name="preciounidad" className="form-control" />
                            </div>

                            <select className="form-select form-select-lg mt-4" name="disponible" onChange={handleChange}>
                                <option value="true" selected={plato.disponible?true:false}>Disponible</option>
                                <option value="false" selected={!plato.disponible?true:false}>No disponible</option>
                            </select>

                            <div className="d-grid gap-2">
                                <button className="btnYellow mt-5 fw-bold fs-4">Modificar</button>
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

export default EditPlato;