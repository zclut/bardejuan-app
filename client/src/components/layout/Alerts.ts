import Swal from 'sweetalert2';

export const AlertModal = (iconf: any, titlef: string, textf: string) => {
    Swal.fire({
        icon: iconf,
        title: titlef,
        text: textf,
        background: '#3b3228',
        color: '#fff',
        confirmButtonColor: '#4c4034',
        focusConfirm: false
    });
};

export const ConfirmModal = (titlef: string, textf: string) => {
    return Swal.fire({
        title: titlef,
        text: textf,
        icon: 'warning',
        showCancelButton: true,
        background: '#3b3228',
        color: '#fff',
        confirmButtonColor: 'rgba(154, 252, 154, .3)',
        cancelButtonColor: 'rgba(255, 178, 178, .4)',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    });
}

export const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})

