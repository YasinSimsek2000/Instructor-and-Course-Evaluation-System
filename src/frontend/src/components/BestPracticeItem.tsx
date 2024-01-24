import React from 'react';
import {MDBTable, MDBTableHead, MDBTableBody, MDBTypography} from 'mdb-react-ui-kit';

interface BestPracticeItemProps {
    items: {
        id: number;
        poster: string;
        date: string;
        link: string;
        title: string;
    }[];
}

const BestPracticeItem: React.FC<BestPracticeItemProps> = ({ items }) => {
    const handleLinkClick = (link: string) => {
        window.location.href = link; // Redirect the user to the provided link
    };

    return (
        <div style={{ overflow: 'auto', maxHeight: '400px' }}>
            <MDBTable align='middle'>
                <MDBTableHead>
                    <tr>
                        <th scope='col'>Title</th>
                        <th scope='col'>Date</th>
                    </tr>
                </MDBTableHead>
                <MDBTableBody>
                    {items.map((item) => (
                        <tr key={item.id}>
                            <td>
                                <MDBTypography note noteColor='secondary'>
                                    <a href={item.link} onClick={(e) => { e.preventDefault(); handleLinkClick(item.link); }}>
                                        <strong>{item.title}</strong>
                                    </a>
                                </MDBTypography>
                            </td>
                            <td>
                                <MDBTypography tag='strong'>{item.date}</MDBTypography>
                            </td>
                        </tr>
                    ))}
                </MDBTableBody>
            </MDBTable>
        </div>
    );
};

export default BestPracticeItem;
