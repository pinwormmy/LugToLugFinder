import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          그냥 한글 써봄
        </p>
      </header>
      <p>개발환경 변경...ㄱㄱ</p>
      <input placeholder='brand'/>

      <table>
        <thead>
          <tr>
            <th>Brand</th>
            <th>Collection</th>
            <th>Ref</th>
            <th>Case(mm)</th>
            <th>Lug to Lug(mm)</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th>Omega</th>
            <th>Constellation Globemaster</th>
            <th>130.30.39.21.03.001</th>
            <th>39</th>
            <th>46.7</th>
          </tr>
          <tr>
            <th>Omega</th>
            <th>Sea Master Aqua Terra</th>
            <th>220.10.41.21.01.002</th>
            <th>41</th>
            <th>48</th>
          </tr>
          <tr>
            <th>Rolex</th>
            <th>Submariner</th>
            <th>126610LN</th>
            <th>41</th>
            <th>47.6</th>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default App;
