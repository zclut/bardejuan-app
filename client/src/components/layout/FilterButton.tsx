import React from 'react'

interface Props {
    handleOnClick: () => void,
    text: string,
    position: string
}

const FilterButton = (props: Props) => {

    const { handleOnClick, text , position} = props;

    return (
        <div className="col-12 col-md-4">
            <div className={position}>
                <div onClick={handleOnClick} className="btnYellow fs-5">{text}</div>
            </div>
        </div>
    );
}

export default FilterButton;