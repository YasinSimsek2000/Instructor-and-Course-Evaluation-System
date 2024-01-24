import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {
    MDBBtn,
    MDBCollapse,
    MDBContainer,
    MDBNavbar,
    MDBNavbarBrand,
    MDBNavbarItem,
    MDBNavbarLink,
    MDBNavbarNav,
    MDBNavbarToggler
} from 'mdb-react-ui-kit';
import axios from "axios";

export const Navigation = () => {
    const navigate = useNavigate();
    const [showNavCentered, setShowNavCentered] = useState(false);


    function LogOut() {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/connection/logout", {
        })
            . then(response => {
                if (response.status == 200) {
                    navigate('/')
                } else {
                    alert(response.data)
                    navigate('/')
                }
            });
    }

    return (
        <MDBNavbar expand='lg' light bgColor='light'>
            <MDBContainer fluid>
                <MDBNavbarBrand href='/home'>ICES4HU</MDBNavbarBrand>
                <MDBNavbarToggler
                    type='button'
                    data-target='#navbarCenteredExample'
                    aria-controls='navbarCenteredExample'
                    aria-expanded='false'
                    aria-label='Toggle navigation'
                    onClick={() => setShowNavCentered(!showNavCentered)}
                >
                </MDBNavbarToggler>
                <MDBCollapse navbar show={showNavCentered}>
                    <MDBNavbarNav fullWidth={false} className='mb-2 mb-lg-0'>
                        <MDBNavbarLink href='/courses'>Courses</MDBNavbarLink>
                        <MDBNavbarLink href='/home'>Departments</MDBNavbarLink>
                        <MDBNavbarLink href='/department-managers'>Department Managers</MDBNavbarLink>
                        <MDBNavbarLink href='/instructors'>Instructors</MDBNavbarLink>
                        <MDBNavbarLink href='/students'>Students</MDBNavbarLink>
                    </MDBNavbarNav>
                </MDBCollapse>

                <div>
                    <MDBNavbarNav className='d-flex flex-row'>
                        <MDBNavbarItem className='me-3 me-lg-0'>
                            <MDBNavbarLink href='/messages'>
                                <i className="fas fa-envelope fa-lg"></i>
                            </MDBNavbarLink>
                        </MDBNavbarItem>
                        <MDBNavbarItem className='me-3 me-lg-0'>
                            <MDBNavbarLink href='/settings'>
                                <i className="fas fa-gear fa-lg"></i>
                            </MDBNavbarLink>
                        </MDBNavbarItem>
                        <MDBNavbarItem className='me-3 me-lg-0'>
                            <MDBNavbarLink href='/manage'>
                                <i className="fas fa-user fa-lg"></i>
                            </MDBNavbarLink>
                        </MDBNavbarItem>
                        <MDBNavbarItem className='me-3 me-lg-0'>
                            <MDBBtn className="mt-1 w-100" onClick={() => LogOut()}>Log Out</MDBBtn>
                        </MDBNavbarItem>
                    </MDBNavbarNav>
                </div>
            </MDBContainer>
        </MDBNavbar>
    )
}