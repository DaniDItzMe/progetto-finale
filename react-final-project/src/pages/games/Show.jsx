import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../../axios";
import { GameCard } from "../../components/GameCard";

export function Show() {
  const [game, setGame] = useState();

  const { id } = useParams();

  useEffect(() => {
    api
      .get(`/api/games/get/${id}`)
      .then((response) => {
        console.log(response.status);
        setGame(response.data);
      })
      .catch((err) => {
        console.log(err.status);
        setGame(null);
      });
  }, []);

  return (
    <>
      <h1 className="my-5 text-center">Product Details</h1>
      {game && <GameCard game={game}></GameCard>}
      {!game && (
        <div>
          <h1 className="text-center text-danger fw-bold">Game not found!</h1>
        </div>
      )}
    </>
  );
}
