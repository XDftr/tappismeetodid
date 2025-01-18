import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Homepage from './Homepage'
import {Container, Nav, Navbar} from 'react-bootstrap'
import AllSubmissions from './AllSubmissions'
import {useEffect} from 'react'

function App() {
    function generateAndSaveUUID() {
        // Generate UUID v4
        const uuid = ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );

        // Save to session storage
        if (!sessionStorage.getItem("uuid")) {
            sessionStorage.setItem('uuid', uuid);
        }
    }

    generateAndSaveUUID()
    useEffect(() => {

        console.log(sessionStorage.getItem("uuid"))
    }, [])

  return (
        <div>
            <BrowserRouter>
                <Navbar style={{backgroundColor:"green"}}>
                    <Container>
                        <Navbar.Brand href="/">ThousandPictures</Navbar.Brand>
                        <Navbar.Toggle aria-controls="basic-navbar-nav" />
                        <Navbar.Collapse id="basic-navbar-nav">
                            <Nav className="me-auto">
                                <Nav.Link href="/">Home</Nav.Link>
                                <Nav.Link href="/all-submissions">All submissions</Nav.Link>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
                <Routes>
                    <Route path="/" element={<Homepage />} />
                    <Route path="/all-submissions" element={<AllSubmissions />} />
                </Routes>
            </BrowserRouter>
        </div>
  );
}

export default App;
