<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Contract\Database;

class MataKuliahController extends Controller
{
    protected $database;
    protected $tablename;
    public function __construct(Database $database)
    {
        $this->database = $database;
        $this->tablename = 'mata_kuliah';
    }
    public function index() {
        $mata_kuliahs = $this->database->getReference($this->tablename)->getValue();
        return view('mata_kuliah',compact('mata_kuliahs'));
        // return dd(compact('users'));
    }

    public function create(Request $request)
    {
        $mata_kuliah_code = $request->code;
    
        $newData = $this->database->getReference($this->tablename . '/' . $mata_kuliah_code)->set([
            'mata_kuliah_name' => $request->nama,
            'mata_kuliah_code' => $request->code,
            // tambahkan semua field yang diperlukan
        ]);
    
        return redirect()->back();
    }
    
    
    public function update(Request $request)
    {
        $this->database->getReference($this->tablename . '/' . $request->code)
        ->update([
                'mata_kuliah_name' => $request->nama,
                'mata_kuliah_code' => $request->code,
                // tambahkan semua field yang diperlukan
            ]);

        return redirect()->back();
    }

    public function delete(Request $request)
    {
        $this->database->getReference($this->tablename . '/' . $request->code)->remove();

        return redirect()->back();
    }

}
