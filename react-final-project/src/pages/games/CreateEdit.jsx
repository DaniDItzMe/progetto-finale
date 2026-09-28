import api from "../../axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { GamesCreateEditForm } from "../../components/GamesCreateEditForm";
import { useLocation } from "react-router-dom";
import { useLoginContext } from "../../contexts/LoginContext";
import { useNavigate } from "react-router-dom";
import { Navigate } from "react-router-dom";

export function CreateEdit() {
  const { id } = useParams();
  const [genres, setGenres] = useState();
  const [consoles, setConsoles] = useState();

  const { auth, loading } = useLoginContext();
  const server = import.meta.env.VITE_SERVER_ROOT;

  const location = useLocation();

  const [edit, setEdit] = useState(
    location.pathname == "/create" ? false : true,
  );

  const navigate = useNavigate();

  const [game, setGame] = useState(
    !edit
      ? {
          name: "",
          description: "",
          genres: [],
          availableConsoles: [],
          developer: "",
          publisher: "",
          coverImage: null,
          price: "",
        }
      : null,
  );

  useEffect(() => {
    if (edit) {
      api
        .get(`/api/games/get/${id}`)
        .then((response) => {
          console.log(response.data);
          setGame(response.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }

    api.get(`/api/genres`, { withCredentials: true }).then((response) => {
      console.log(response.data);

      setGenres(response.data);
    });

    api.get(`/api/consoles`).then((response) => {
      setConsoles(response.data);
    });
  }, []);

  return (
    <>
      {auth?.role === "ADMIN" && (
        <>
          {edit && <h1 className="my-5 text-center">Edit {game?.name}</h1>}
          {!edit && <h1 className="my-5 text-center">Create new game</h1>}
          {game && genres && consoles && (
            <div className="d-flex justify-content-center">
              <GamesCreateEditForm
                game={game}
                genres={genres}
                consoles={consoles}
                edit={edit}
              ></GamesCreateEditForm>
            </div>
          )}

          {!game && edit && (
            <div>
              <h1 className="text-center fw-bold text-danger">
                Game not found!
              </h1>
            </div>
          )}
        </>
      )}

      {auth?.role != "ADMIN" && !loading && <Navigate to={"/404"}></Navigate>}
    </>
  );
}
