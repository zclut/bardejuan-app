import { IPlato } from "./IPlato";

export interface IDetalleFactura {
    idfactura?:   number;
    plato?:       IPlato;
    cantidad?:    number;
    preciototal: number;
}