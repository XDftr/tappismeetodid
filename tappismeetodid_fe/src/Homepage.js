import {Col, InputGroup, Row, Form, Button, Image, Spinner} from 'react-bootstrap'
import Modal from 'react-bootstrap/Modal';
import {useState} from 'react'
import axios from 'axios'

export default function Homepage() {

    const [formData, setFormData] = useState({
        name: '',
        title: '',
        country: ''
    });

    const [inputValue, setInputValue] = useState('');
    const [loading, setLoading] = useState(false)
    const [saving, setSaving] = useState(false)
    const [imageUrl, setImageUrl] = useState('')
    const [imageId, setImageId] = useState('')
    const [showModal, setShowModal] = useState(false)

    const handleClose = () => setShowModal(false);
    const handleShow = () => setShowModal(true);

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleChange = (e) => {
        setInputValue(e.target.value);
    };

    const getSubmissions = async () => {
        setLoading(true)
        await generateImage()
        setLoading(false)
    }


    const generateImage = async () => {
        const body = {
            text: inputValue,
            author: sessionStorage.getItem("uuid")
        }
        await axios.post("http://localhost:8080/api/image", body)
            .then(async response => {
                console.log("Generate image response: ", response);
                setImageUrl(await getImageUrl(response.data.imageId));
            })
            .catch(ex => {
                alert(ex.response.data.message);
                setLoading(false);
            });

    }

    const getImageUrl = async (id) => {
        setImageId(id)

        const response = await axios.get(`http://localhost:8080/api/image/${id}`)

        console.log("Get image response: ", response)
        return `http://localhost:8080/images/generated/${response.data.imageUrl}`
    }

    const saveSubmissions = async () => {
        setSaving(true)
        handleClose()
        if(!imageUrl) {
            alert("You cannot submit without image!");
            setSaving(false)
            return
        }

        const data = {
            name: formData.name,
            country: formData.country,
            title: formData.title,
            image: imageId
        }
        await axios.post("http://localhost:8080/api/submissions", data).catch(() => {
            alert("Unknown server error!");
        }).then(() => {
            setFormData({
                name: '',
                title: '',
                country: ''
            })
            setImageUrl("")
            setInputValue("")
        })
        setSaving(false)
    }

    return (

        <div className="container mt-4">
            <Row>
                <Col md={7}>
                    <InputGroup size="lg" className="mb-3">
                        <Form.Control
                            as="textarea"
                            size="lg"
                            placeholder="Enter AI prompt here"
                            value={inputValue}
                            onChange={handleChange}
                            aria-label="Large input"
                        />
                    </InputGroup>
                </Col>
                <Col>
                    <Button onClick={() => getSubmissions()} disabled={loading}>
                        Generate image
                    </Button>
                </Col>
                <Col>
                    <Button onClick={() => handleShow()} disabled={loading || !imageUrl} variant="success">
                        Submit image
                    </Button>
                </Col>
            </Row>
            <Row style={{marginBottom: "30px"}}>
                {!loading && imageUrl && <Image src={imageUrl}/>}
                {loading && <Spinner/>}

            </Row>


            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Submit your image</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {(!formData.name || !formData.title || !formData.country) && <div style={{color:"red"}}>Please fill all fields below!</div>}
                    <InputGroup className="mb-3">
                        <InputGroup.Text>Name</InputGroup.Text>
                        <Form.Control
                            placeholder="Enter your name"
                            name="name"
                            value={formData.name}
                            required={true}
                            onChange={handleFormChange}
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text>Title</InputGroup.Text>
                        <Form.Control
                            placeholder="Enter your title"
                            name="title"
                            value={formData.title}
                            required={true}
                            onChange={handleFormChange}
                        />
                    </InputGroup>

                    <InputGroup className="mb-3">
                        <InputGroup.Text>Country</InputGroup.Text>
                        <Form.Control
                            name="country"
                            placeholder="Enter your country"
                            value={formData.country}
                            required={true}
                            onChange={handleFormChange}
                        >
                        </Form.Control>
                    </InputGroup>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button
                        variant="primary"
                        onClick={saveSubmissions}
                        disabled={!formData.name || !formData.title || !formData.country || saving}
                    >
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}