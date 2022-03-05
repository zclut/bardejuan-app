import React, { useState, useEffect } from 'react';
import client from '../../config/axios';
import { IMesa } from '../../interfaces/IMesa';
import desk from '../../assets/img/desk.svg';
import CreateButton from '../layout/CreateButton';
import { Link } from 'react-router-dom';

const ListMesas = () => {

    const [mesas, setMesas] = useState<IMesa[]>([] as IMesa[]);


    useEffect(() => {
        const getMesas = async () => {
            const response = await client.get('v2/mesas');
            setMesas(response.data);
        }
        getMesas();
    }, [])

    return (
        <>
            <div className='container'>

                <h1 className='display-3 text-white text-center'>Lista de Mesas</h1>

                <div className="row">
                    {mesas.map((mesa: IMesa) => {

                        const { nummesa, ocupantesmax } = mesa;

                        return (
                            <div className="col-md-3 col-sm-6 col-12 mt-4" key={nummesa}>
                                <div className="card h-100 shadow-sm" >

                                    <div className="position-absolute top-0 start-0 ms-2 mt-2">
                                        <Link to={`/mesas/${mesa.nummesa}/edit`}>
                                            <div className="fa-solid fa-pencil h1 editButton"></div>
                                        </Link>
                                    </div>

                                    <div className="text-center">
                                        <div className="img-hover-zoom img-hover-zoom--colorize">
                                            <img className="shadow" src={desk} alt="Imagen mesa" />
                                        </div>
                                    </div>

                                    <div className="card-body">
                                        <div className="clearfix mb-3"></div>

                                        <div className="my-2 text-center">
                                            <h1>Mesa Nº {nummesa}</h1>
                                        </div>

                                        <div className="mb-3">
                                            <h2 className="text-uppercase text-center role">Ocupantes Maximos {ocupantesmax}</h2>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>

            <CreateButton route="/mesas/create" text="mesa" />
        </>
    );
}

export default ListMesas;