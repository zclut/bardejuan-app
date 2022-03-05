export function formatDate(miliseconds: any): string {
    const myDate = new Date(miliseconds);
    return myDate.getFullYear() + '-' + ('0' + (myDate.getMonth() + 1)).slice(-2) + '-' + ('0' + myDate.getDate()).slice(-2) + ' ' + myDate.getHours() + ':' + ('0' + (myDate.getMinutes())).slice(-2) + ':' + myDate.getSeconds()
}

export function valueInput(miliseconds: any): string{
    const myDate = new Date(miliseconds);
    
    return myDate.toISOString().substring(0, 16);
}