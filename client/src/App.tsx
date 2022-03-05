import { BrowserRouter, Route, Routes } from 'react-router-dom';
import EditDetalle from './components/detalles/EditDetalle';
import NewDetalle from './components/detalles/NewDetalle';
import Footer from './components/layout/Footer';
import Header from './components/layout/Header';
import Home from './components/layout/Home';
import PageNotFound from './components/layout/PageNotFound';
import EditMesa from './components/mesas/EditMesa';
import ListMesas from './components/mesas/ListMesas';
import NewMesa from './components/mesas/NewMesa';
import EditPlato from './components/platos/EditPlato';
import ListPlatos from './components/platos/ListPlatos';
import NewPlato from './components/platos/NewPlato';
import PrivateRoute from './components/private/PrivateRoute';
import EditServicio from './components/servicios/EditServicio';
import ListServicios from './components/servicios/ListServicios';
import NewServicio from './components/servicios/NewServicio';
import Servicio from './components/servicios/Servicio';

function App() {
  return (
    <BrowserRouter>

      <Header />

      <div className="main-wrapper">
        <Routes>
          <Route path="/" element={<Home />} />

          <Route path="/mesas" element={
            <PrivateRoute>
              <ListMesas />
            </PrivateRoute>
          } />

          <Route path="/mesas/create" element={
            <PrivateRoute>
              <NewMesa />
            </PrivateRoute>
          } />

          <Route path="/mesas/:id/edit" element={
            <PrivateRoute>
              <EditMesa />
            </PrivateRoute>
          } />

          <Route path="/platos" element={<ListPlatos />} />

          <Route path="/platos/create" element={
            <PrivateRoute>
              <NewPlato />
            </PrivateRoute>
          } />

          <Route path="/platos/:id/edit" element={
            <PrivateRoute>
              <EditPlato />
            </PrivateRoute>
          } />

          <Route path="/servicios" element={
            <PrivateRoute>
              <ListServicios />
            </PrivateRoute>
          } />

          <Route path="/servicios/:id" element={
            <PrivateRoute>
              <Servicio />
            </PrivateRoute>
          } />

          <Route path="/servicios/create" element={
            <PrivateRoute>
              <NewServicio />
            </PrivateRoute>
          } />

          <Route path="/servicios/:id/edit" element={
            <PrivateRoute>
              <EditServicio />
            </PrivateRoute>
          } />

          <Route path="/servicios/:idservicio/platos/create" element={
            <PrivateRoute>
              <NewDetalle />
            </PrivateRoute>
          } />

          <Route path="/servicios/:idservicio/platos/:idfactura/edit" element={
            <PrivateRoute>
              <EditDetalle />
            </PrivateRoute>
          } />

          <Route path="*" element={<PageNotFound />} />

        </Routes>


        <Footer />
      </div>

    </BrowserRouter>
  );
}

export default App;