import { IDetalleFactura } from './IDetalleFactura';
import {IMesa} from './IMesa';

export interface IServicio {
    idservicio?: number;
    fechacomienzo?: number;
    fechafin?: number;
    reservada?: string;
    pagada?: boolean;
    mesa?: IMesa;
    ocupantes?: number;
    detalleFactura?: IDetalleFactura[];
}