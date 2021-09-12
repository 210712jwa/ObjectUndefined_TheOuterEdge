import { Form } from './Form'

export const forms: Form[] = [
    {
        id: 1, 
        title: "UFOs Above National Mall",
        description: "Bright lights hover above white House",
        submitted: 20210202,
        eventTime: 20210203,
        image: 5,
        latitude: 33.9,
        longitude: 77.0,
        author: "samantha123",
        likes: 2,
        dislikes: 2,
        verifier: "Rob", 
        formStatus: true,
        comments: "Wowzers"
    }, 
    {
        id: 2, 
        title: "Flashing Lights Off Coast of Boston",
        description: "Flashing lights off horizon of Boston, Marblehead",
        submitted: 20210202,
        eventTime: 20210203,
        image: 4,
        latitude: 42.5,
        longitude: 70.9,
        author: "tina123",
        likes: 2,
        dislikes: 2,
        verifier: "Rob", 
        formStatus: true,
        comments: "Wowzers"
    }, {
        id: 3, 
        title: "UFOs Above Manhattan",
        description: "Saucer zooms over Hell's Kitchen",
        submitted: 20210202,
        eventTime: 20210203,
        image: 3,
        latitude: 40.8,
        longitude: 74.0,
        author: "denise123",
        likes: 2,
        dislikes: 2,
        verifier: "Rob", 
        formStatus: false,
        comments: "Wowzers"
    }
]